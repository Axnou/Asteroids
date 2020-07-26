# Asteroids
Ohjelma tarvitsee oikein toimiakseen Java JDK 11:sta tai uudemman sekä JavaFX:n.
IntelliJ ohjelmointiympäristössä Java FX polku lisätääån ohjelmistoon seuraavalla tavalla:
File > Project Structure > ADD (+) > Java > Asema\JavaFXKansio\JavaFX\javafx-sdk-11.0.2\lib

Tämän lisäksi Vm options täytyy päivittää oikeaksi:
Run > Edit Configurations > VM options: > lisää teksti: --module-path Asema\JavaFXKansio\JavaFX\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml > apply
