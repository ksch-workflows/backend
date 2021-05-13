# REST API

## Create patient

```
curl http://localhost:8080/api/patients -X POST
```

Example response:

```
{
  "id": "fb3c2796-eebd-4541-bf48-5d9f168f2c86",
  "name": "John Doe",
  "nameFather": null,
  "dateOfBirth": "1996-05-13",
  "gender": "MALE",
  "address": "Guesthouse"
}
```

## List all patiensts

```
curl http://localhost:8080/api/patients
```

Example response:

```
[
  {
    "id": "49215f6b-6a1a-49f1-808a-02ed5cc45299",
    "name": "John Doe",
    "nameFather": null,
    "dateOfBirth": "1996-05-13",
    "gender": "MALE",
    "address": "Guesthouse"
  },
  {
    "id": "a60cfc5b-bf81-4fe0-b851-4d283dcbd702",
    "name": "John Doe",
    "nameFather": null,
    "dateOfBirth": "1996-05-13",
    "gender": "MALE",
    "address": "Guesthouse"
  }
]
```
