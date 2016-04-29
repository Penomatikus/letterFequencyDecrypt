# letterFequencyDecrypt
Little tool I've wrote to decrypt some encrypted files for the course "Data protection and data safety" at HTW Berlin.
  
###Usage  
`java -jar LetterFreqency.jar <path to encrypted file> <path to store decrypted file>`

After your first run, the program gives you a estimated preview of the decrypted file. Moreover, it will show you the translation table, it used to get this decryption. This table was calculated by the letter frequency of the given text.    

`Translate table:`     
`Crypted:| A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | W | X | Y | Z |`    
`Normal: | R | Y | Q | O | S | T | H | U | C | V | M | F | X | J | E | A | Z | B | D | I | P | K | L | G | N | W |`  
  
However, if you are not satisfied with the result, you can make changes to that table and rerun the decryption as often you would like to.  
`Do you want to make changes to the table? ( help|exit )>`   

The syntax is: LETTERALETTERB<enter-key>, which is literally "Replace the value of Crypted Letter "LETTERA" with "LETTERB".   
If you write "SH" this would mean to the upper table: Take the "D" from "S" and replace it with a "H".  
Moreover, the programm will search for a letter in the crypted row with the value "H" and replace this value with a "D".  





