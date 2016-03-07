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
		