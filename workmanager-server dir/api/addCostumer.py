from google.appengine.ext.webapp import template

import webapp2
import json
from models.user import User
from models.costumer import Costumer


class addCostumer(webapp2.RequestHandler):
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
			
		costumer=Costumer.query(Costumer.name==name, Costumer.user==user.mail).get()
		if costumer:
			self.error(402)
			self.response.write(name+' already exist')
			return
		
		costumer=Costumer()
		costumer.user=user.mail
		costumer.name=name
		costumer.address=address
		costumer.phone=phone
		costumer.email=email
		costumer.field1=field1
		costumer.field2=field2
		costumer.field3=field3
		costumer.field4=field4
		costumer.field5=field5
		costumer.put()
		self.response.write(json.dumps({"status": "OK"}))


app = webapp2.WSGIApplication([
	('/api/addcostumer',addCostumer)
], debug=True)