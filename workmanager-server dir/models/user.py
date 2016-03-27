from google.appengine.ext import ndb
import hashlib      #we need this to safely store passwords
import logging

class User(ndb.Model):
	mail = ndb.StringProperty()
	code = ndb.StringProperty()
	password = ndb.StringProperty()
	managerPricing = ndb.IntegerProperty()
	workerPricing = ndb.IntegerProperty()

	@staticmethod
	def checkToken(token):
		user = User.get_by_id(long(token))
		return user

	def setPassword(self, password):
		self.password = hashlib.md5(password).hexdigest()
		self.put()
		
	def setCode(self, code):
		self.code = hashlib.md5(code).hexdigest()
		self.put()

	def checkPassword(self, password):
		if not password:
			return False
		logging.info("self.pass: {}, hashed pass: {}".format(self.password, hashlib.md5(password).hexdigest()))
		if self.password == hashlib.md5(password).hexdigest():
			return True
		return False
		
	def getPassword(self):
		return self.password
		
	@staticmethod
	def checkIfUesr(email):
		user=User.query(User.email == email).get()
		if user:
			return user
		return None
		
	@staticmethod
	def checkIfCode(code):
		user=User.query(User.code == code).get()
		if user:
			return user
		return None

	@staticmethod
	def remove(mail):
		user=User.query(User.mail == mail).get()			
		if user is not None:
			user.key.delete();
		return
		
	@staticmethod
	def addUser(mail, password, code, managerPricing, workerPricing):
		user=User()
		user.mail=mail
		user.password=password
		user.code=code
		user.managerPricing=managerPricing
		user.workerPricing=workerPricing
		user.put()
		return
		
	@staticmethod	
	def updateManagerPricing(mail, managerPricing):
		user=User.query(User.mail == mail).get()	
		password=user.password
		code=user.code
		workerPricing= user.workerPricing
		User.remove(mail)
		User.addUser(mail, password, code, managerPricing, workerPricing)
		return
		
	@staticmethod	
	def updateWorkerPricing(mail, workerPricing):
		user=User.query(User.mail == mail).get()	
		password=user.password
		code=user.code
		managerPricing= user.managerPricing
		User.remove(mail)
		User.addUser(mail, password, code, managerPricing, workerPricing)
		return
		
		
		