from analyze_transcripts import calculate_sentence_length
from analyze_transcripts import DataAnalysis

def test_calculate_sentence_length():
    
    # Example Data
    string = ["one."]
    dialogue = DataAnalysis(string)
    filtered_lines = dialogue.filter_B()
    
    # Expected Result:
    expected_rttr = 3
    
    # Running the function
    result = calculate_sentence_length(filtered_lines)
    print(result)
    
    # Assertion
    assert result == expected_rttr
