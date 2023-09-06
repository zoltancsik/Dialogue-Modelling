from nltk.tokenize import word_tokenize
from collections import Counter

class DataAnalysis:
    def __init__(self, lines):
        self.lines = lines

    def filter_B(self):
        """
        Filters and returns lines from the instance's `lines` attribute that start with 'B: '. 
        The 'B: ' prefix is removed from the returned lines.

        Returns:
        - list of str: A list of lines that originally started with 'B: '.
        """
        filtered_lines = [line[3:] for line in self.lines if line.startswith('B: ')]
        return filtered_lines
    
    def count_unique_words(self, lines):
        """
        Count the number of unique words in the provided lines, excluding certain punctuations.
        
        Parameters:
        - lines (str): The sentence to be analyzed.

        Returns:
        - int: The number of unique words found in the provided lines, excluding '.', ',', or '?'.
        """
        words = word_tokenize(lines)
        words_stripped = []
        for char in words:
            if char not in ['.', ',', '?']:
                words_stripped.append(char)
        unique_words = set(Counter(words_stripped))
        return len(unique_words)

if __name__ == "__main__":
    with open('../transcribed_data/HC1.txt', 'r') as file:
        lines = file.readlines()
        
    dialogue = DataAnalysis(lines)
    filtered_lines = dialogue.filter_B()
    
    total_unique_words = 0
    for line in filtered_lines:
        unique_words_per_line = dialogue.count_unique_words(line)
        total_unique_words += unique_words_per_line
        # print(line, end='')
        # print("NUM:", unique_words_per_line)
        
    average_unique_words = total_unique_words / len(filtered_lines) if filtered_lines else 0
    print(f"Total number of sentences: {len(filtered_lines)}.")
    print(f"Average number of unique words per sentence: {round(average_unique_words,2)}.")
    print(f"Calculation: sum_of_unique_value/total_number_of_sentences.")
    print(f"In Numbers: {total_unique_words}/{len(filtered_lines)}.")
