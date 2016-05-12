#!/usr/bin/python
import MySQLdb
from random import randint

# Open database connection
db = MySQLdb.connect("grubhunterdb.crbi4rfww47t.us-west-2.rds.amazonaws.com","root","rootroot","grubhunterdb" )

# prepare a cursor object using cursor() method
cursor = db.cursor()
ratings = ["0.5","1.0",'1.5','2.0','2.5','3.0','3.5','4.0','4.5','5.0']

for i in range(0,5000):
    dishes = []
    email = "test"+str(i)+"@nyu.edu"
    sql = "INSERT INTO user (email,password,first_name,last_name,phone) values ('"+email+"','testpass','Test','Test','1231231231')"
    try:
        cursor.execute(sql)
        db.commit()
    except Exception as e:
        print "Error: unable save user"
        print str(e)
    
    for j in range(0,6):
        res_id = randint(1,1000)
        # Prepare SQL query to INSERT a record into the database.
        sql = "SELECT dish_id FROM restaurant JOIN restaurant_dishes on (id=restaurant_id) where id=%d" % (res_id)
        try:
            cursor.execute(sql)
            results = cursor.fetchall()
            for row in results:
                dishes.append(row[0])
        except Exception as e:
            print "Error: unable to fecth data"
            print str(e)
        if len(dishes) > 0:
            dish_id = dishes[randint(0,len(dishes)-1)]
            rate = ratings[randint(0,9)]
            sql = "INSERT INTO dish_preferences(email,dish_id) values ('%s',%s)"% (email,dish_id)
            try:
                cursor.execute(sql)
                db.commit()
                print email,str(dish_id),str(res_id),rate
                nsql = "INSERT INTO user_rating(email,restaurant_id,dish_id,dish_rating) values ('%s',%s,%s,'%s')" % (email,res_id,dish_id,rate)
                print sql
                try:
                    cursor.execute(nsql)
                    db.commit()
                except Exception as e:
                    print "Error: unable save user rating"
                    print str(e)
            except Exception as e:
                print str(e)
# disconnect from server
db.close()
