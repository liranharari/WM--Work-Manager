from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User
from models.customer import Customer


class addCustomer(webapp2.RequestHandler):
	def get(self):
		mail=self.request.get('mail')
		name=self.request.get('name')
		address=self.request.get('address')
		phone=self.request.get('phone')
		email=self.request.get('email')
		field1=self.request.get('field1')
		field2=self.request.get('field2')
		field3=self.request.get('field3')
		field4=self.request.get('field4')
		field5=self.request.get('field5')
		
		user = User.query(User.mail == mail).get()
		if not user:
			self.error(402)
			self.response.write('ERROR')
			return
			
		customer=Customer.query(Customer.name==name, Customer.user==user.mail).get()
		if customer:
			self.error(402)
			self.response.write(name+' already exist')
			return
		
		customer=Customer()
		customer.user=user.mail
		customer.name=name
		customer.address=address
		customer.phone=phone
		customer.email=email
		customer.field1=field1
		customer.field2=field2
		customer.field3=field3
		customer.field4=field4
		customer.field5=field5
		customer.put()
		self.response.write(json.dumps({"status": "OK"}))


app = webapp2.WSGIApplication([
	('/api/addcustomer',addCustomer)
], debug=True)