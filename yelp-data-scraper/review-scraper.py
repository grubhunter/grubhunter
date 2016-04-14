import urllib2, csv
from bs4 import BeautifulSoup
import sys

print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)

cnt = 1;
with open("restaurents.csv","r") as in_file:
    content = in_file.readlines()

with open("error-"+sys.argv[1]+".txt","w") as err_file:
    for line in content:
        try:
            start_at = 0
            if cnt >= int(sys.argv[1]) and cnt <= int(sys.argv[2]):
                entry = line.strip().split(',')
                with open("data/"+entry[0]+".csv","w") as out_file:
                    writer = csv.writer(out_file, delimiter=",")
                    no_of_reviews = int(entry[6].replace('reviews','').strip())
                    while start_at < no_of_reviews:
                        start = "https://www.yelp.com"
                        start = start+entry[7]+'?start='+str(start_at)
                        print start
                        page = urllib2.urlopen(start)
                        soup = BeautifulSoup(page,"html.parser")
                        result = soup.find_all("div",class_="review-wrapper")
                        for rev_entry in result:
                            rating_tag = rev_entry.find("meta")
                            review_tag = rev_entry.find("p")
                            if rating_tag != None and review_tag != None:
                                rating = rating_tag['content'].strip().encode("utf-8")
                                review = review_tag.get_text().strip().encode("utf-8")
                                writer.writerow([review,rating])
                        start_at = start_at+10
        except Exception as e:
            err_file.write(line+"\n")
            err_file.write(e)
            err_file.write("\n\n")    
        cnt = cnt + 1



