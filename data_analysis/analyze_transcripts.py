class DataAnalysis:
    def __init__(self, filename):
        self.filename = filename

    def filter_B(self):
        with open(self.filename, 'r') as file:
            lines = file.readlines()
            filtered_lines = [line for line in lines if line.startswith('B: ')]
        return filtered_lines

if __name__ == "__main__":
    dialogue = DataAnalysis('../transcribed_data/HC1.txt')
    
    # Get all lines with 'B: ' and print them
    filtered_lines = dialogue.filter_B()
    for line in filtered_lines:
        print(line, end='')
