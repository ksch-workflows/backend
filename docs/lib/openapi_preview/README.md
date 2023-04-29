# OpenAPI Preview

## Swagger UI

OpenAPI files can be reviewed with [Swagger UI](https://swagger.io/tools/swagger-ui/):

```sh
docker run --rm -p 5000:8080 --name swaggerui \
  -v $(pwd)/docs:/open-apis \
  -e SWAGGER_JSON=/open-apis/openapi.yml \
  swaggerapi/swagger-ui
```

WARNING: OpenAPI 3.1 not supported, yet. See https://swagger.io/blog/swagger-support-for-openapi-3-0-and-openapi-3-1/

## References
