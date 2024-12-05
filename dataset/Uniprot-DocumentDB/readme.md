Complete Uniprot Dataset in JSON Format for Document DB (MongoDB)
Here is the complete Uniprot dataset in JSON format, prepared for Document DB upload to MongoDB (excluding PubMed data).
The original Uniprot data has been cleaned up to remove unnecessary annotations in various fields.
All the code can be found in script.py.


script_PubMed.py
This script queries the PubMed API for each protein. Since the process is time-consuming, it has been implemented as follows:
1. Takes start_index as input, indicating the first protein to process.
2. Makes a request to PubMed for all PubMed_IDs associated with a given protein.
3. Loads the result directly into the output.
4. Prints a counter showing the currently processed protein.
5. Moves on to the next protein.

This implementation allows you to stop the requests at any time and resume them later
