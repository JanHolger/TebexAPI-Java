# TebexAPI-Java
A java client for Tebex

## Usage
```java
TebexAPI api = new TebexAPI("YOUR_SECRET_KEY");
TebexQueue queue = new TebexQueue(api, new TebexListener(){
    public boolean onOfflineCommand(TebexCommand command){
        // EXECUTE THE COMMAND
        return true;
    }
    public boolean onOnlineCommand(TebexCommand command){
        if(isPlayerOnline(command.player.uuid)){
            // EXECUTE THE COMMAND
            return true;
        }
        return false;
    }
});
queue.setOfflineInterval(30000); // OPTIONAL (DEFAULT: 30000), INTERVAL IN WHICH TO CHECK OFFLINE COMMANDS
queue.setOnlineEnabled(true); // OPTIONAL (DEFAULT: false), WHETHER TO SUPPORT ONLINE COMMANDS
new Thread(queue, "Tebex").start();
```

## Maven (Jitpack)
Repo:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Dependency:
```xml
<dependency>
    <groupId>com.github.JanHolger</groupId>
    <artifactId>TebexAPI-Java</artifactId>
    <version>2d7da16243</version>
</dependency>
```
