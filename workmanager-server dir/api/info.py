from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User
from models.customer import Customer
from models.field import Field


class getUserInfo(webapp2.RequestHandler):
	def get(self):
  		template_params={}
		mail=self.request.get('mail')
		
		user= User.query(User.mail == mail).get()
		if not user:
			self.error(403)
			self.response.write('Error')
			return
		
		template_params['code']=user.code
		template_params['managerPricing']=user.managerPricing
		template_params['workerPricing']=user.workerPricing
		self.response.write(json.dumps(template_params))

class getCustomerInfo(webapp2.RequestHandler):
	def get(self):
  		template_params={}
		mail=self.request.get('mail')
		name=self.request.get('name')
		
		user= User.query(User.mail == mail).get()
		if not user:
			self.error(403)
			self.response.write(' user Error')
			return
		customer=Customer.query(Customer.name==name, Customer.user==mail).get()
		if not customer:
			self.error(403)
			self.response.write('no customer')
			return
		
		template_params['address']=customer.address
		template_params['phone']=customer.phone
		template_params['email']=customer.email
		template_params['field1']=customer.field1
		template_params['field2']=customer.field2
		template_params['field3']=customer.field3
		template_params['field4']=customer.field4
		template_params['field5']=customer.field5
		self.response.write(json.dumps(template_params))

class getUserCustomers(webapp2.RequestHandler):
	def get(self):
		template_params={}
		mail=self.request.get('mail')
		
		user= User.query(User.mail == mail).get()
		if not user:
			self.error(403)
			self.response.write(' user Error')
			return
		customers=Customer.getAllCustomersPerUser(user.mail)
		
		
		
		template_params['status']="OK"
		template_params['customers']=customers
		self.response.write(json.dumps(template_params))
		
class getUserCustomersAndHours(webapp2.RequestHandler):
	def get(self):
		template_params={}
		mail=self.request.get('mail')
		
		user= User.query(User.mail == mail).get()
		if not user:
			self.error(403)
			self.response.write(' user Error')
			return
		hours=Customer.getAllCustomersAndHoursPerUser(user.mail)
		
		
		template_params['status']="OK"
		template_params['customersandhours']=hours
		self.response.write(json.dumps(template_params))

class getUserFields(webapp2.RequestHandler):
	def get(self):
		template_params={}
		mail=self.request.get('mail')
		
		user= User.query(User.mail == mail).get()
		if not user:
			self.error(403)
			self.response.write(' user Error')
			return
		fields=Field.getAllFieldsPerUser(user.mail)
		
		
		template_params['status']="OK"
		template_params['fields']=fields
		self.response.write(json.dumps(template_params))

class getCodes(webapp2.RequestHandler):
	def get(self):
		template_params={}
		code=self.request.get('code')
		
		user= User.query(User.code == code).get()
		if user:
			self.error(403)
			self.response.write('taken code')
			return
		
		template_params['status']="OK"
		self.response.write(json.dumps(template_params))

app = webapp2.WSGIApplication([
	('/api/getuserinfo',getUserInfo),
	('/api/getcustomerinfo',getCustomerInfo),
	('/api/getusercustomers', getUserCustomers),
	('/api/getusercustomersandhours', getUserCustomersAndHours),
	('/api/getuserfields', getUserFields),
	('/api/getcodes', getCodes)
], debug=True)