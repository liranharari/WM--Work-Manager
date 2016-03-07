#this model keeps the link
from google.appengine.ext import ndb
from user import User
class Costumer(ndb.Model):
	user = ndb.KeyProperty(kind=User)
	name = ndb.StringProperty() 
	address = ndb.StringProperty()
	phone = ndb.StringProperty()
	email = ndb.StringProperty()
	field1 = ndb.StringProperty()
	field2 = ndb.StringProperty()
	field3 = ndb.StringProperty()
	field4 = ndb.StringProperty()
	field5 = ndb.StringProperty()
	field6 = ndb.StringProperty()

	@staticmethod
	def getCostumer(user, name):
		costumer=Costumer.query(Costumer.user == user , Costumer.name == name).get()
		if costumer:
			return costumer
		return None
		
	@staticmethod
	def getAllCostumersPerUser(user):
		costumers=[]
		qur=Costumer.query(Costumer.user == user.key )#.order(-Link.time_of_enter_the_link)
		if qur:
			for name in qur:
				costumers.append(name)
			return costumers
		return None
	
	@staticmethod
	def remove(user, name):
		costumer=Costumer.getCostumer(user, name)		
		if costumer is not None:
			costumer.key.delete();
		return
		
	@staticmethod
	def addcostumerfromuser(user, name, address, phone, email, field1, field2, field3, field4, field5, field6):
		costumer=Costumer()
		costumer.name=name
		costumer.address=address
		costumer.user=user.key
		costumer.phone=phone
		costumer.email=email
		costumer.field1=field1
		costumer.field2=field2
		costumer.field3=field3
		costumer.field4=field4
		costumer.field5=field5
		costumer.field6=field6
		costumer.put()
		return