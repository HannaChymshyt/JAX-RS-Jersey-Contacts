TEST REST API
---------------

GET
http://localhost:8082/api/v1.0/contacts

GET
http://localhost:8082/api/v1.0/contacts/2

POST
http://localhost:8082/api/v1.0/contacts

{
    "id": 5,
    "name": "Darth",
    "phone": "1-403-646-7733"
}


PUT
http://localhost:8082/api/v1.0/contacts/2

{
    "phone": "1-587-998-32-41"
}


DELETE
http://localhost:8082/api/v1.0/contacts/3