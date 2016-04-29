# letterFequencyDecrypt
Little tool I've wrote to decrypt some encrypted files for the course "Data protection and data safety" at HTW Berlin.  

**SHA256** LetterFrequency.jar: 43f31f55519e07cb3dff347987b8d9650e56777084f67f74144ee66a480d4dc2

###Attention!  
Since this was only for my cource, the code will just work under the following circumstances:  
* The encrypted string stores a German language conform text
* The encrypted string do only have upper case letters
* The encrypted string do only user [A-Z]  
  
You can try it with the example text file.
  
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
  
  
### Example decripher: 
>hänsel und gretel saßen an dem feuer, bis mittag, da aß jedes sein stücklein brod, und dann wieder bis an den abend: aber vater und mutter blieben aus, und niemand wollte kommen und sie abholen. wie es nun finstere nacht wurde, fing gretel an zu weinen, hänsel aber sprach: »wart nur ein weilchen, bis der mond aufgegangen ist.« und als der mond aufgegangen war, faßte er die gretel bei der hand, da lagen die kieselsteine wie neugeschlagene batzen und schimmerten und zeigten ihnen den weg. da gingen sie die ganze nacht durch, und wie es morgen war, kamen sie wieder bei ihres vaters haus an. der vater freute sich von herzen, als er seine kinder wieder sah, denn er hatte sie ungern allein gelassen, die mutter stellte sich auch, als wenn sie sich freute, heimlich aber war sie bös.nicht lange darnach, war wieder kein brod im hause und hänsel und gretel hörten wie abends die mutter zum vater sagte: »einmal haben die kinder den weg zurückgefunden, und da habe ichs gut seyn lassen, aber jetzt ist wieder nichts, als nur noch ein halber laib brod im haus, du mußt sie morgen tiefer in den wald führen, daß sie nicht wieder heim kommen können, es ist sonst keine hülfe für uns mehr.«





