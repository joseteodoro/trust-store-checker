Truststore tester

This tool check if a given truststore are correct to connect on the given host. Required JDK8+.

You can download the last built version [here](https://ibm.box.com/s/ptrvcpfcj8a5nrjzbhotbwj3t3dx2bhz).

Usage:

```
 java -jar truststore-checker.jar  truststore-file=file.jks truststore-password=password https-url=https://localhost:8888
```

Valid parameters:

- truststore-file: path to the truststore to be tested; ex. (truststore-file=./file.jks)
- truststore-password: password to the truststore to be tested; ex. (truststore-password=MyPassword)
- https-url: URL to be used on the test; ex. (https-url=https://localhost:8888)
- version: the TLS version to use; ex. (version=TLSv1.2)
- provider: the SSL connection provider. ex (provider=SunX509)

The following values will be used as default:

- https-url: "https://localhost:443";
- truststore-file: "./truststore.jks";
- truststore-password: "password";
- provider: "SunX509";
- version: "TLSv1.2".


If the truststore works you will receive the console output:

```
######################################################
##               Truststore works!                  ##
######################################################
```

Otherwise, will output what' wrong with the truststore and the connection. Good luck!