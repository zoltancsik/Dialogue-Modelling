from analyze_transcripts import DataAnalysis
from utilities import strip_sentence
from utilities import remove_contractions

def test_filter_B():
    # Test, whether filtering out the participants's sentences only work.
    da = DataAnalysis(["B: Hello", "A: World", "B: How are you?"])
    assert da.filter_B() == ["Hello", "How are you?"]

def test_count_words():
    # Test, whether the correct number of words is counted in a sentence:
    da = DataAnalysis([])
    assert da.count_words("This is a sentence with seven words.") == 7
    assert da.count_words("Five words in this one?") == 5

def test_count_unique_words():
    # Test, whether the correct number of unique words is counted
    da = DataAnalysis([])
    assert da.count_unique_words("Well, You know, I mean, well, you you know well, what I mean!") == 6
    assert da.count_unique_words("If, if I say, If I agree, I help?") == 5

def test_sentence_length():
    # Test, whether the correct length of a sentence, based on characters is counted.
    # Account for strip_sentence removing characterst that are not supposed to be counted.
    da = DataAnalysis([])
    assert da.sentence_length(strip_sentence("I don't know if it was the very first time.")) == 32
    assert da.sentence_length(strip_sentence("Which movie, can you repeat?")) == 22

def test_tokenize_line():
    # Test, whether the input is tokenized correctly and whether punctuations and contractions are handled correctly.
    da = DataAnalysis([])
    assert da.tokenize_line("Would you be interested in watching it again?") == ["Would", "you", "be", "interested", "in", "watching", "it", "again"]
    assert da.tokenize_line("I did not hear anything..") == ["I", "did", "not", "hear", "anything", ".."]
