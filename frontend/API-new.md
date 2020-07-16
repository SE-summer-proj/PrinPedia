# API (2020-07-16 Added)

## Create Entry

* **URL:** `'/create'`

* **Method:** `POST`

* **Request body:**

```JavaScript
{
    keyword: "新词条名称"
}
```

* **Response:**

```JSON
{
    "extraData": ..., // the newly created entry[1]
    "message": "Successfully created",
    "status": 0
}
```

## Edit Entry

* **URL:** `'/edit'`

* **Merhod:** `POST`

* **Request body:**

```JavaScript
{
    contents: ..., // the modified new contents
}
```

* **Response:**

```JSON
{
    "extraData": ..., // the modified new contents
    "message": "Successfully edited",
    "status": 0
}
```

## Notes

1. The format of default new entry should be like this:

```JavaScript
{
    title: "新词条名称",
    summary: "",
    content: []
}
```
