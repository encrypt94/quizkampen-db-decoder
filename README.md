Quizkampen database decoder
===========================

A simple and dirty tool to decode Quizkampen (a.k.a QuizDuello, QuizClash ecc...) database.

Requirements
------------

  * [adb](https://developer.android.com/tools/help/adb.html)
  * java: version 8 for java.util.Base64
  * [SQLite JDBC Driver](https://github.com/xerial/sqlite-jdbc)

Usage
-----

### Get it

`git clone https://github.com/encrypt94/quizkampen-db-decoder.git`

### Build it

`javac *.java `

### Get the database from mobile device

`adb root`

`adb pull /data/data/se.feomedia.quizkampen.it.lite/databases/quizkampen /home/your-user/quizkampen`

### Use it

`java QuizkampenDatabaseDecoder ~/quizkampen`