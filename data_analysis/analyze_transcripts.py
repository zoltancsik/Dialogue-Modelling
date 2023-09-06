from nltk.tokenize import word_tokenize

class DataAnalysis:
    def __init__(self, filename):
        self.filename = filename

    def filter_B(self):
        with open(self.filename, 'r') as file:
            lines = file.readlines()
            filtered_lines = [line for line in lines if line.startswith('B: ')]
        return filtered_lines
    
    def count_unique_words(self, sentence):
        # Tokenizing the sentence using NLTK's word_tokenize
        words = [word.lower() for word in word_tokenize(sentence)]  
        unique_words = set(words)  # Use a set to get the unique words
        return len(unique_words)

if __name__ == "__main__":
    dialogue = DataAnalysis('../transcribed_data/HC1.txt')
    
    filtered_lines = dialogue.filter_B()
    for line in filtered_lines:
        print(line, end='')
        print("Number of unique words:", dialogue.count_unique_words(line))
