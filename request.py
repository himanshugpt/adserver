import urllib2
import json

'''
POST request example using python 2.7 
can be done using curl
curl -v -H "X-ApiKey: xxx" -H "Content-Type: application/json" --data '{}'  http://localhost:8080/ad/post1
'''
url = 'http://localhost:8080/ad/create' 
#post_fields = {'key': 'value'}  
post_fields={'content': 'Best marker ever'}
data = json.dumps(post_fields)
request = urllib2.Request(url, data)
request.add_header('X-ApiKey', 'xxx')
request.add_header('Content-Type', 'application/json')
request.add_header('Content-Length', len(data))

response = urllib2.urlopen(request)
data = response.read()
print data

#url = 'http://localhost:8080/ad/create'
