# ir_hw2
I don't change the anything in the Class "Path"
So you need to create a folder called "data" in the same path of folder "src"
Save "result.trectext" and "result.trecweb" into folder "data"
Also, create folder "indextext" and "indexweb" in the folder "data" which is the place of result
Finally, just run the class "Main"


You will have totally six files, three in the folder "indextext" and three in the folder "indexweb"
file "Dictionary Term" stores token and its collection frequency
file "docNoIdMapping" stores new index id and its original docNo
file "Posting" stores three things : token, the document id which contains this token, frequency
last two are the posting list for token
in one line



# get result like as followed:

finish 30000 docs
finish 60000 docs
finish 90000 docs
finish 120000 docs
finish 150000 docs
finish 180000 docs
totaly document count:  198361
index web corpus running time: 0.6960666666666666 min
 >> the token "acow" appeared in 3 documents and 3 times in total
       lists-092-3952951    154963         1
      lists-108-11347927    186006         1
       lists-092-4113429    154964         1
load index & retrieve running time: 0.0344 min
finish 30000 docs
finish 60000 docs
finish 90000 docs
finish 120000 docs
finish 150000 docs
finish 180000 docs
finish 210000 docs
finish 240000 docs
finish 270000 docs
finish 300000 docs
finish 330000 docs
finish 360000 docs
finish 390000 docs
finish 420000 docs
finish 450000 docs
finish 480000 docs
totaly document count:  503473
index text corpus running time: 2.1608 min
 >> the token "yhoo" appeared in 5 documents and 5 times in total
        NYT19990208.0397    291085         1
        NYT20000717.0201    477373         1
        NYT20000927.0406    502701         1
        NYT19990405.0253    313384         1
        NYT20000928.0343    503146         1
load index & retrieve running time: 0.07395 min

