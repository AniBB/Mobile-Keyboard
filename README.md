# Mobile-Keyboard
Autocomplete Simulation

Initialize a new AutoComplete object: AutoComplete ac = new AutoComplete();

To train an AutoComplete object with a passage, call "train" on an AutoComplete object with the desired passage: ac.train(passage);

To get all words matching a fragment from an AutoComplete object, call "getWords" on the object with the desired fragment, and save the result in a List of CandStrings (ArrayList recommended):
  ArrayList<CandString> list = ac.getWords(fragment);
  
To get the String of a Candidate, call "getWord" on an existing Candidate. To get the confidence of a Candidate, call "getConfidence" on it. The "toString" method of CandString returns the word in the CandString, followed by a space, followed by its confidence in parentheses.

If an AutoComplete object has already been trained with a passage, and you would like to train a new passage without losing old information, call "train" on the existing object. Newly-created AutoComplete objects contain no information.
