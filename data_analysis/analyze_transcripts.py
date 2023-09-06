from nltk.tokenize import word_tokenize
from collections import Counter

class DataAnalysis:
    def __init__(self, lines):
        self.lines = lines

    def filter_B(self):
        filtered_lines = [line[3:] for line in self.lines if line.startswith('B: ')]
        return filtered_lines
    
    def count_unique_words(self, lines): 
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
    for line in filtered_lines:
        print(line, end='')
        print("Number of unique words:", dialogue.count_unique_words(line))
