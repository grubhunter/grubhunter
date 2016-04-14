import urllib2, csv
from bs4 import BeautifulSoup

start_at = 0

with open("restaurents.csv","w") as out_file:
    while(start_at < 1000):
        print "Fetching results "+str(start_at)+" to "+str(start_at+10)+"...."
        start = "https://www.yelp.com/search?find_desc=Restaurants&find_loc=Brooklyn,+NY&start="+str(start_at)
        page = urllib2.urlopen(start)
        soup = BeautifulSoup(page,"html.parser")

        result = soup.find_all("li",class_="regular-search-result")
        writer = csv.writer(out_file, delimiter=",")
        for entry in result:
            biz_name = entry.find("a",class_="biz-name").span.get_text().strip().encode("utf-8")
            
            price_tag = entry.find("span",class_="business-attribute price-range")
            price = ""
            if price_tag != None:
                price = price_tag.get_text().strip().encode("utf-8")
            
            near_tag = entry.find("div",class_="secondary-attributes").span
            near = ""
            if near_tag != None:
                near = near_tag.get_text().strip().encode("utf-8")
            
            address = ""
            address_tag = entry.find("div",class_="secondary-attributes").address
            if address_tag != None:
                address = address_tag.get_text().strip().encode("utf-8")
                
            rating=""
            rating_tag = entry.find("img",class_="offscreen")
            if rating_tag != None:
                rating = rating_tag['alt'].strip().encode("utf-8")
            
            reviews = ""
            review_tag = entry.find("span",class_="review-count rating-qualifier")
            if review_tag != None:
                reviews = review_tag.get_text().strip().encode("utf-8")
            
            link = entry.find("a",class_="biz-name")['href'].encode("utf-8")
        
            writer.writerow([biz_name,price,near,address,rating,reviews,link])
        start_at = start_at + 10