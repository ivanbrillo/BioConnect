1. filter.py extracts the fields we are interested in from full_database.xml and saves them in a CSV file (I used CSV because it's lighter).
2. Use any online converter to transform the CSV file into JSON.
3. With a Ctrl+F, replace .. with , (I couldn’t use , in the CSV file because , is the delimiter used to separate fields in CSV. I replaced it with ..).
4. cleanJSON processes specific fields by converting strings and arrays.