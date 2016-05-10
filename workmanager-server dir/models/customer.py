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
	managertime= ndb.IntegerProperty()
	workertime= ndb.IntegerProperty()

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
	def getAllCustomersAndHoursPerUser(user):
		q = Customer.query(Customer.user == user)
		customers_arr = []
		for customer in q:
			customers_arr.append({"name": customer.name,
				"managertime": customer.managertime, "workertime": customer.workertime})
		return customers_arr
	
	@staticmethod
	def remove(user, name):
		customer=Customer.query(Customer.user == user , Customer.name == name).get()	
		if customer is not None:
			customer.key.delete();
		return
	
	@staticmethod	
	def updateCustomer(user, name, newname, address, phone, email, field1, field2, field3, field4, field5, field6):
		customer=Customer.query(Customer.user == user , Customer.name == name).get()	
		managertime= customer.managertime
		workertime= customer.workertime
		Customer.remove(user, name)
		Customer.addcustomerfromuser(user, newname, address, phone, email, field1, field2, field3, field4, field5, field6, managertime, workertime)
		return
		
	@staticmethod	
	def updateCustomerManagerHours(user, name, hoursToAdd):
		customer=Customer.query(Customer.user == user , Customer.name == name).get()	
		managertime= customer.managertime +hoursToAdd
		name=customer.name
		address=customer.address
		user=customer.user
		phone=customer.phone
		email=customer.email
		field1=customer.field1
		field2=customer.field2
		field3=customer.field3
		field4=customer.field4
		field5=customer.field5
		field6=customer.field6
		workertime= customer.workertime
		Customer.remove(user, name)
		Customer.addcustomerfromuser(user, name, address, phone, email, field1, field2, field3, field4, field5, field6, managertime, workertime)
		return

	@staticmethod	
	def updateCustomerWorkerHours(user, name, hoursToAdd):
		customer=Customer.query(Customer.user == user , Customer.name == name).get()	
		workertime= customer.workertime +hoursToAdd
		name=customer.name
		address=customer.address
		user=customer.user
		phone=customer.phone
		email=customer.email
		field1=customer.field1
		field2=customer.field2
		field3=customer.field3
		field4=customer.field4
		field5=customer.field5
		field6=customer.field6
		managertime= customer.managertime
		Customer.remove(user, name)
		Customer.addcustomerfromuser(user, name, address, phone, email, field1, field2, field3, field4, field5, field6, managertime, workertime)
		return

	@staticmethod	
	def resetCustomerHours(user, name):
		customer=Customer.query(Customer.user == user , Customer.name == name).get()	
		managertime= 0
		workertime= 0
		name=customer.name
		address=customer.address
		user=customer.user
		phone=customer.phone
		email=customer.email
		field1=customer.field1
		field2=customer.field2
		field3=customer.field3
		field4=customer.field4
		field5=customer.field5
		field6=customer.field6
		Customer.remove(user, name)
		Customer.addcustomerfromuser(user, name, address, phone, email, field1, field2, field3, field4, field5, field6, managertime, workertime)
		return
		
	@staticmethod
	def addcustomerfromuser(user, name, address, phone, email, field1, field2, field3, field4, field5, field6, managertime, workertime):
		customer=Customer()
		customer.name=name
		customer.address=address
		customer.user=user
		customer.phone=phone
		customer.email=email
		customer.field1=field1
		customer.field2=field2
		customer.field3=field3
		customer.field4=field4
		customer.field5=field5
		customer.field6=field6
		customer.managertime=managertime
		customer.workertime=workertime
		customer.put()
		return