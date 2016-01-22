# MemeCreator

Summary:

This is an android application that creates a meme.

Features:

Users can select pictures from outside sources such as the gallery or email, or take a new picture, and proceed to add captions with a distinct font that produce a meme. Users can also save the meme to the gallery in a new “meme” folder, or share the created meme using any image-related application. This makes heavy use of file streams and intents, mostly using Java. Formatting of the graphical components of the app were completed using XML.
 
Files:

-MainActivty.java : This drives the entire application can contains all of the functions for choosing a picture, creating the meme, saving the meme, and sharing the meme.

-TopActivity.java : This file contains code for the input text fields and buttons of the application. A fragment of MainActiviy.java.

-BottomActivity.java : This file contains code for the displaying of the actual meme, including all of the text formatting displayed on the  meme. Also a fragment of MainActivity.java.
