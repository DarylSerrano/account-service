# Installing, testing and runing
```bash
./mvnw install 

./mvnw test 

./mvnw spring-boot:run 

```

# Design decisions 
* Treasury boolean set as column not updatable in order to prevent changes, to change, must use Update directly via using database a query
* Currency not implemented, but initial design was either:
    * using an API for getting exchange rates and only saving into Account model the name of the currency, creating ExhangeRateService
    * creating another table Currencies with columns name, and code of currency and then have a ManyToOne relationship between Account and Currency (There is a branch called `feature/currency` with the partial implementation)

