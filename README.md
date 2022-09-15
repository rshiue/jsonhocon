# jsonhocon

An extreme simple converter to convert Json File to Hocon File, or vice versa.

# Build

Require JDK 8 and Maven.

Run: 

````mvn package````
 

# Usage

Download the jar file and run the following command:

### Json to Hocon

```sh
java -cp json-hocon.jar net.shiue.HoconJsonConverter HOCON "path/to/source.json" "path/to/dest.conf" 
```

### Hocon to Json

```sh
java -cp json-hocon.jar net.shiue.HoconJsonConverter JSON "path/to/source.conf" "path/to/dest.json" 
```

### License

MIT
