import bcrypt
import json
import uuid
import random
import string
from tqdm import tqdm

# Funzione per generare una password crittografata
def generate_password_hash(password):
    return bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')

# Funzione per generare una password casuale
def generate_random_password(length=10):
    characters = string.ascii_letters + string.digits
    return ''.join(random.choice(characters) for _ in range(length))

# Funzione per generare un commento fittizio
def generate_comment():
    comments = [
        "This is a simple comment",
        "Another insightful comment",
        "Yet another comment",
        "This comment is different",
        "A unique comment for testing"
    ]
    element_ids = [
        "P68871", "P69905", "P00734", "A0A0C5B5G6", "DB00001", "DB00002", "DB00003"
    ]

    protein = ["P68871", "P69905", "P00734", "A0A0C5B5G6"]
    elemId = random.choice(element_ids)

    if elemId in protein:
        return {
            "_id": str(uuid.uuid4()),
            "comment": random.choice(comments),
            "uniProtID": elemId
        }
    else:
        return {
            "_id": str(uuid.uuid4()),
            "comment": random.choice(comments),
            "drugBankID": elemId
        }

# Genera un utente con o senza commenti
def generate_user(user_id, password, role, num_comments=0):
    user = {
        "_id": user_id,
        "password": generate_password_hash(password),
        "role": role,
        "comments": [generate_comment() for _ in range(num_comments)],
        "_class": "org.unipi.bioconnect.model.User"
    }
    return user

# Genera una lista di utenti fittizi
def generate_users():
    users = [
        generate_user("admin", "password", "ADMIN"),
        generate_user("test_user1", "password", "REGISTERED", num_comments=3),
        generate_user("test_user2", "password", "REGISTERED", num_comments=5)
    ]

    # Genera circa 500 utenti con password casuali
    for i in tqdm(range(3, 503), desc="Generating users"):
        user_id = f"user{i}"
        password = generate_random_password()
        users.append(generate_user(user_id, password, "REGISTERED", num_comments=random.randint(0, 5)))

    return users

# Scrive gli utenti in un file JSON
def write_users_to_json(filename):
    users = generate_users()
    with open(filename, "w") as file:
        json.dump(users, file, indent=4)
    print(f"File '{filename}' creato con successo.")

# Genera e scrive il file JSON
write_users_to_json("users.json")
