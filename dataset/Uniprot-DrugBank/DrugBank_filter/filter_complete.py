from xml.sax.handler import ContentHandler
from xml.sax import parse
import pandas as pd

class ExtractData(ContentHandler):
    def __init__(self):
        self.drug_data = {}
        self.curr_id = ""
        self.limit = 0
        self.current_target = None
        self.current_target_actions = set()


    # found a character
    def characters(self, content):
        if self.limit == 2:
            self.curr_id = content
            self.drug_data[self.curr_id] = {
                'name': '',
                'target_actions': set()
            }
            self.limit = 3

        elif self.limit == 4:
            if content.strip():
                if self.drug_data[self.curr_id]['name']:
                    self.drug_data[self.curr_id]['name'] += content
                else:
                    self.drug_data[self.curr_id]['name'] = content

        elif self.limit == 12:
            if content.strip():
                self.current_target_actions.add(content.strip())

    # found a start element
    def startElement(self, name, attrs):
        if name == "drug":
            self.limit = 1
            
        if self.limit == 1 and name == "drugbank-id" and attrs:
            if attrs["primary"] == "true":
                self.limit = 2

        elif self.limit == 3 and name == "name":
            self.limit = 4
            self.drug_data[self.curr_id]['name'] = ''

        elif name in ("target", "enzyme", "carrier", "transporter"):
            self.current_target_actions = set()

        elif name == "polypeptide":
            if 'id' in attrs:
                self.current_target = attrs["id"]
        
        elif name == "action":
            self.limit = 12


    # found end element
    def endElement(self, name):
        if name == "name":
            # Sostituisci le virgole con punti nel nome quando finisce l'elemento name
            if self.drug_data[self.curr_id]['name']:
                self.drug_data[self.curr_id]['name'] = self.drug_data[self.curr_id]['name'].replace(',', '.')
            self.limit = 0
            
        elif name in ("target", "enzyme", "carrier", "transporter"):
            if self.current_target:
                if self.current_target_actions:
                    # Se ci sono azioni, associa ogni azione al target (o enzima)
                    for action in self.current_target_actions:
                        target_action_pair = f"{self.current_target}:{action}"
                        self.drug_data[self.curr_id]['target_actions'].add(target_action_pair)
                else:
                    # Se non ci sono azioni, aggiungi la proteina con 'NULL' come azione
                    target_action_pair = f"{self.current_target}:NULL"
                    self.drug_data[self.curr_id]['target_actions'].add(target_action_pair)
            
            # Reset variabili dopo la fine del target (o enzima)
            self.current_target = None
            self.current_target_actions = set()
            self.limit = 0

        elif name == "action":
            self.limit = 0

    # found end document
    def endDocument(self):
        data_for_df = []
        for drug_id, data in self.drug_data.items():
            target_actions_str = ';'.join(sorted(data['target_actions'])) if data['target_actions'] else ''
            data_for_df.append({
                'DrugBank_ID': drug_id,
                'Name': data['name'].strip(),
                'Target_Actions': target_actions_str
            })

        # SAVE IN A CSV FILE
        df = pd.DataFrame(data_for_df)
        df.to_csv('drugbank_complete.csv', index=False)

parse('full_database.xml', ExtractData())
