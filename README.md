# Mobile-Keyboard
Autocomplete Simulation

This was my attempt at a programming challenge found on the following website: https://www.asymmetrik.com/programming-challenges/ 

The purpose of these files is to generate a list of words and their confidences (frequencies) the way autocomplete on a cell phone might. For example, if the stored text is "The thing is, I think that this thing is too thin" and the passed-in fragment is "th", all words starting with "th" (case-insensitive) will be returned, along with the number of times each word occurs in the saved text, in order of frequency (lexicographic ordering ignored). In this example, the following list would be returned: 

[thing (2), the (1), think (1), this (1), thin (1), that (1)].

*******************************************************************************************************************************************
Instructions: 

Initialize a new AutoComplete object: AutoComplete ac = new AutoComplete();

To train an AutoComplete object with a passage, call "train" on an AutoComplete object with the desired passage: ac.train(passage);

To get all words matching a fragment from an AutoComplete object, call "getWords" on the object with the desired fragment, and save the result in a List of Candidates (ArrayList recommended): ArrayList<CandString> list = ac.getWords(fragment);
  
To get the String of a Candidate, call "getWord" on an existing Candidate. To get the confidence of a Candidate, call "getConfidence" on it. The "toString" method of CandString returns the word in the CandString, followed by a space, followed by its confidence in parentheses.

If an AutoComplete object has already been trained with a passage, and you would like to train a new passage without losing old information, call "train" on the existing object. Newly-created AutoComplete objects contain no information.
