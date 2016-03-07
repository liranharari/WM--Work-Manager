#this model keeps the link
from google.appengine.ext import ndb
from user import User
class Customer(ndb.Model):
	user = ndb.StringProperty() 
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
	def getcustomer(user, name):
		customer=Customer.query(Customer.user == user , Customer.name == name).get()
		if customer:
			return customer
		return None
		
	@staticmethod
	def getAllCustomersPerUser(user):
		q = Customer.query(Customer.user == user)
		customers_arr = []
		for customer in q:
			customers_arr.append({"name": customer.name})
		return customers_arr
	
	@staticmethod
	def remove(user, name):
		customer=Customer.getCustomer(user, name)		
		if customer is not None:
			customer.key.delete();
		return
		
	@staticmethod
	def addcustomerfromuser(user, name, address, phone, email, field1, field2, field3, field4, field5, field6):
		customer=Customer()
		customer.name=name
		customer.address=address
		customer.user=user.key
		customer.phone=phone
		customer.email=email
		customer.field1=field1
		customer.field2=field2
		customer.field3=field3
		customer.field4=field4
		customer.field5=field5
		customer.field6=field6
		customer.put()
		return