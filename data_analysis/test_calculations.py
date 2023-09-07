from analyze_transcripts import DataAnalysis

def test_filter_B():
    # Test, whether filtering out the participants's sentences only work.
    da = DataAnalysis(["B: Hello", "A: World", "B: How are you?"])
    assert da.filter_B() == ["Hello", "How are you?"]

def test_count_words():
    # Test, whether the correct number of words is counted in a sentence:
    da = DataAnalysis([])
    assert da.count_words("This is a sentence with seven words.") == 7
    assert da.count_words("Five words in this one?") == 5
