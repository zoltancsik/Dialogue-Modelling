from analyze_transcripts import DataAnalysis

def test_filter_B():
    # Test, whether filtering out the participants's sentences only work.
    da = DataAnalysis(["B: Hello", "A: World", "B: How are you?"])
    assert da.filter_B() == ["Hello", "How are you?"]
