## Configuration body

> Das enthaellt die vom Client ausgewaehlte Equipment Konfiguration und die ID des empfaengers.\
> Wird von allen Services verwendet und weitergereicht.

```py
{
    "request_id": <client-identifier>,
    "category_liquid": 
        {"Oil": <string>,
         "fuel":<string>},
    "category_motor": 
        {"gearbox": <string>,
         "engine": <string>,
         "starting system": <string>,
         "engine": <string>},
    "category_software": 
        {"monitoring": <string>,
         "fuel": <string>},
}
```

## Result/Status body

```py
{
"request_id": <client-identifier>,
"equipment" : {"name": <string>,
                "result": {ok, failed}}
}

```