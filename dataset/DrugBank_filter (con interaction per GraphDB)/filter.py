from xml.sax.handler import ContentHandler
from xml.sax import parse
import pandas as pd

class ExtractData(ContentHandler):
    def __init__(self):
        self.drug_data = {}
        self.curr_id = ""
        self.limit = 0
        self.curr_patent = None
        self.curr_interaction = None
        self.curr_reference = None

    def characters(self, content):
        if self.limit == 2:
            self.curr_id = content
            self.drug_data[self.curr_id] = {
                'name': '',
                'description': '',
                'toxicity': '',
                'category': '',
                'patents': [],
                'sequence': '',
                'drug_interaction': [],
                'references': []
            }
            self.limit = 3

        elif self.limit == 4:
            if content.strip():
                if self.drug_data[self.curr_id]['name']:
                    self.drug_data[self.curr_id]['name'] += content
                else:
                    self.drug_data[self.curr_id]['name'] = content

        elif self.limit == 6:
            if content.strip():
                if self.drug_data[self.curr_id]['description']:
                    self.drug_data[self.curr_id]['description'] += content
                else:
                    self.drug_data[self.curr_id]['description'] = content


        
        elif self.limit == 10:
            if content.strip():
                if self.curr_reference['ID']:
                    self.curr_reference['ID'] += content
                else:
                    self.curr_reference['ID'] = content
        
        elif self.limit == 12:
            if content.strip():
                if self.curr_reference['num']:
                    self.curr_reference['num'] += content
                else:
                    self.curr_reference['num'] = content

        elif self.limit == 14:
            if content.strip():
                if self.curr_reference['info']:
                    self.curr_reference['info'] += content
                else:
                    self.curr_reference['info'] = content
        

        
        elif self.limit == 17:
            if content.strip():
                if self.drug_data[self.curr_id]['toxicity']:
                    self.drug_data[self.curr_id]['toxicity'] += content
                else:
                    self.drug_data[self.curr_id]['toxicity'] = content

        elif self.limit == 20:
            if content.strip():
                if self.drug_data[self.curr_id]['category']:
                    self.drug_data[self.curr_id]['category'] += ":" + content
                else:
                    self.drug_data[self.curr_id]['category'] = content
        

        
        elif self.limit == 24:
            if content.strip():
                if self.curr_patent['number']:
                    self.curr_patent['number'] += content
                else:
                    self.curr_patent['number'] = content

        elif self.limit == 26:
            if content.strip():
                if self.curr_patent['country']:
                    self.curr_patent['country'] += content
                else:
                    self.curr_patent['country'] = content

        elif self.limit == 28:
            if content.strip():
                if self.curr_patent['approved']:
                    self.curr_patent['approved'] += content
                else:
                    self.curr_patent['approved'] = content


        elif self.limit == 33:
            if content.strip():
                if self.curr_interaction['id']:
                    self.curr_interaction['id'] += content
                else:
                    self.curr_interaction['id'] = content

        elif self.limit == 35:
            if content.strip():
                if self.curr_interaction['name']:
                    self.curr_interaction['name'] += content
                else:
                    self.curr_interaction['name'] = content

        elif self.limit == 37:
            if content.strip():
                if self.curr_interaction['description']:
                    self.curr_interaction['description'] += content
                else:
                    self.curr_interaction['description'] = content 



        elif self.limit == 40:
            if content.strip():
                if self.drug_data[self.curr_id]['sequence']:
                    self.drug_data[self.curr_id]['sequence'] += content
                else:
                    self.drug_data[self.curr_id]['sequence'] = content  



    def startElement(self, name, attrs):
        if name == "drug":
            self.limit = 1
            
        if self.limit == 1 and name == "drugbank-id" and attrs:
            if attrs["primary"] == "true":
                self.limit = 2

        elif self.limit == 3 and name == "name":
            self.limit = 4
            self.drug_data[self.curr_id]['name'] = ''

        elif self.limit == 5 and name == "description": 
            self.limit = 6
            self.drug_data[self.curr_id]['description'] = ''



        elif self.limit == 7 and name == "general-references":
            self.limit = 8

        elif self.limit == 8 and name in {"article", "textbook", "link", "attachment"}:
            self.limit = 9
            self.curr_reference = {
                'type': name,
                'ID': '',
                'num': '',
                'info': ''
            }
        
        elif self.limit == 9 and name == "ref-id":
            self.limit = 10
            self.curr_reference['ID'] = ''

        elif self.limit == 11 and name in {"pubmed-id", "title", "isbn"}:
            self.limit = 12
            self.curr_reference['num'] = ''

        elif self.limit == 13 and name in {"citation", "url"}:
            self.limit = 14
            self.curr_reference['info'] = ''
        


        elif self.limit == 16 and name == "toxicity": 
            self.limit = 17
            self.drug_data[self.curr_id]['toxicity'] = ''



        elif self.limit == 18 and name == "categories":
            self.limit = 19

        elif self.limit == 19 and name == "category": 
            self.limit = 20


        
        elif self.limit == 21 and name == "patents":
            self.limit = 22

        elif self.limit == 22 and name == "patent":
            self.limit = 23
            self.curr_patent = {
                'number': '',
                'country': '',
                'approved': ''
            }

        elif self.limit == 23 and name == "number":
            self.limit = 24
            self.curr_patent['number'] = ''

        elif self.limit == 25 and name == "country":
            self.limit = 26
            self.curr_patent['country'] = ''

        elif self.limit == 27 and name == "approved":
            self.limit = 28
            self.curr_patent['approved'] = ''


        
        elif self.limit == 30 and name == "drug-interactions":
            self.limit = 31

        elif self.limit == 31 and name == "drug-interaction":
            self.limit = 32
            self.curr_interaction = {
                'id': '',
                'name': '',
                'description': ''
            }
        
        elif self.limit == 32 and name == "drugbank-id":
            self.limit = 33
            self.curr_interaction['id'] = ''

        elif self.limit == 34 and name == "name":
            self.limit = 35
            self.curr_interaction['name'] = ''

        elif self.limit == 36 and name == "description":
            self.limit = 37
            self.curr_interaction['description'] = ''

        
        elif self.limit == 39 and name == "sequence" and attrs:
            if attrs["format"] == "FASTA":
                self.limit = 40
                self.drug_data[self.curr_id]['sequence'] = ''
        
        

    def endElement(self, name):
        if name == "drug":
            self.limit = 0

        elif name == "name" and self.limit == 4:
            if self.drug_data[self.curr_id]['name']:
                self.drug_data[self.curr_id]['name'] = self.drug_data[self.curr_id]['name'].replace(',', '..')
            self.limit = 5

        elif name == "description" and self.limit == 6:
            if self.drug_data[self.curr_id]['description']:
                self.drug_data[self.curr_id]['description'] = self.drug_data[self.curr_id]['description'].replace(',', '..')
            self.limit = 7


        
        elif name == "ref-id" and self.limit == 10:
            if self.curr_reference['ID']:
                self.curr_reference['ID'] = self.curr_reference['ID'].replace(',', '..')
            self.limit = 11
   
        elif name in {"pubmed-id", "title", "isbn"} and self.limit == 12:
            if self.curr_reference['num']:
                self.curr_reference['num'] = self.curr_reference['num'].replace(',', '..')
            self.limit = 13

        elif name in {"citation", "url"} and self.limit == 14:
            if self.curr_reference['info']:
                self.curr_reference['info'] = self.curr_reference['info'].replace(',', '..')
            self.limit = 15
        
        elif name in {"article", "textbook", "link", "attachment"} and self.limit == 15:    
            ref_info = f"[{self.curr_reference['type']}:{self.curr_reference['ID']}:{self.curr_reference['num']}:{self.curr_reference['info']}]"
            self.drug_data[self.curr_id]['references'].append(ref_info)
            self.curr_reference = None
            self.limit = 8
        
        elif name == "general-references":
            self.limit = 16



        elif name == "toxicity" and self.limit == 17:
            if self.drug_data[self.curr_id]['toxicity']:
                self.drug_data[self.curr_id]['toxicity'] = self.drug_data[self.curr_id]['toxicity'].replace(',', '..')
            self.limit = 18

        elif name == "category" and self.limit == 20:
            if self.drug_data[self.curr_id]['category']:
                self.drug_data[self.curr_id]['category'] = self.drug_data[self.curr_id]['category'].replace(',', '..')
            self.limit = 19

        elif name == "categories":
            self.limit = 21


        
        elif name == "number" and self.limit == 24:
            if self.curr_patent['number']:
                self.curr_patent['number'] = self.curr_patent['number'].replace(',', '..')
            self.limit = 25

        elif name == "country" and self.limit == 26:
            if self.curr_patent['country']:
                self.curr_patent['country'] = self.curr_patent['country'].replace(',', '..')
            self.limit = 27

        elif name == "approved" and self.limit == 28:
            if self.curr_patent['approved']:
                self.curr_patent['approved'] = self.curr_patent['approved'].replace(',', '..')
            self.limit = 29

        elif name == "patent" and self.limit == 29:
            patent_info = f"[{self.curr_patent['number']}:{self.curr_patent['country']}:{self.curr_patent['approved']}]"
            self.drug_data[self.curr_id]['patents'].append(patent_info)
            self.curr_patent = None
            self.limit = 22
        
        elif name == "patents":
            self.limit = 30
        

        
        elif name == "drugbank-id" and self.limit == 33:
            if self.curr_interaction['id']:
                self.curr_interaction['id'] = self.curr_interaction['id'].replace(',', '..')
            self.limit = 34

        elif name == "name" and self.limit == 35:
            if self.curr_interaction['name']:
                self.curr_interaction['name'] = self.curr_interaction['name'].replace(',', '..')
            self.limit = 36

        elif name == "description" and self.limit == 37:
                if self.curr_interaction['description']:
                    self.curr_interaction['description'] = self.curr_interaction['description'].replace(',', '..')
                self.limit = 38
    
        elif name == "drug-interaction" and self.limit == 38:
            inter_info = f"[{self.curr_interaction['id']}:{self.curr_interaction['name']}:{self.curr_interaction['description']}]"
            self.drug_data[self.curr_id]['drug_interaction'].append(inter_info)
            self.curr_interaction = None
            self.limit = 31
            
        elif name == "drug-interactions":
            self.limit = 39

        
        elif name == "sequence" and self.limit == 40:
            if self.drug_data[self.curr_id]['sequence']:
                self.drug_data[self.curr_id]['sequence'] = self.drug_data[self.curr_id]['sequence'].replace(',', '..')
            self.limit = 41
        
            
    
    def endDocument(self):
        data_for_df = []
        for drug_id, data in self.drug_data.items():
            data_for_df.append({
                'DrugBank_ID': drug_id,
                'Name': data['name'].strip(),
                'Description': data['description'].strip(),
                'Toxicity': data['toxicity'].strip(),
                'Categories': data['category'].strip(),
                'Patents': ';'.join(data['patents']),
                'Sequence': data['sequence'].strip(),
                'Interactions': ';'.join(data['drug_interaction']),
                'References': ';'.join(data['references'])
            })

        
        df = pd.DataFrame(data_for_df)
        df.to_csv('drugbank.csv', index=False)
        #df.to_csv('prova.csv', index=False)

parse('full_database.xml', ExtractData())
#parse('prova.xml', ExtractData())
