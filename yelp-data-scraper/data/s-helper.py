import os

files = os.listdir('.')
for each_file in files:
    altered_content = []
    if each_file != 's-helper.py':
        with open(each_file,'r') as f:
            contents = f.readlines()
            for line in contents:
                if line.strip() != '':
                    if not line.startswith('"'):
                        altered_content.append('"'+line[:line.rfind(',')]+'"'+line[line.rfind(','):])
                    else:
                        altered_content.append(line)
        with open(each_file,'w') as f:
            for line in altered_content:
                f.write(line+"\n")                    