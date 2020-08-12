# Hacker News API Implementation

This is a spring-boot implementation of hacker news public API i.e [Link](https://github.com/HackerNews/API) . 

## Installation

For Ubuntu 18.04, go through these steps to run project on your system

a) Install Docker -

```bash
sudo apt update
sudo apt install docker-ce
```
Credits - [digital ocean]( https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-18-04) 

b) Install maven - 

```bash
sudo apt update
sudo apt install maven
```
c) Run migrations - 
```bash
 mvn clean flyway:migrate -Dflyway.user=postgres -Dflyway.password=postgres -Dflyway.url=jdbc:postgresql://localhost:5432/newsdb
 
```
d) build the project -

```bash
mvn clean package -Dmaven.test.skip=true
```
e) Now go to project-root folder and run - 

```bash
docker build --tag=hackernewsapp-java:base --rm=true .
```
f) Now go to project-root/docker folder and run - 

```bash
docker-compose up .
```
cheers :)
Now you will see the logs of docker services running up.

You can access the API's on port - http://localhost:8080.

## API documentation

**A) Get Best Stories API (Cached) -**
```bash
URL - http://localhost:8080/news/best-stories (GET)
```
This API will return the list of top 10 best stories (based on highest number of comments).
**Response** - 
```json
 {
            "title": "Mozilla lays off 250 employees while it refocuses on commercial products",
            "by": "rebelwebmaster",
            "time": "1597154636",
            "score": "1209",
            "url": "https://blog.mozilla.org/blog/2020/08/11/changing-world-changing-mozilla/",
            "id": 24120336,
            "userName": "rebelwebmaster",
            "created_at": "1597154636"
 }
```
*Cached - This API is cached for 15 minutes refresh time using memcached as a caching service.*

**b) Get Past Stories API -**
```bash
URL - http://localhost:8080/news/past-stories (GET)
```
This API will return the list of past stories from db except current top ten stories from database.

**Response** - 
```json
 {
            "title": "Mozilla lays off 250 employees while it refocuses on commercial products",
            "by": "rebelwebmaster",
            "time": "1597154636",
            "score": "1209",
            "url": "https://blog.mozilla.org/blog/2020/08/11/changing-world-changing-mozilla/",
            "id": 24120336,
            "userName": "rebelwebmaster",
  }   
 ```     
 **c) Get Top Ten Comments for a story (Cached) -**
 
 This API will return the top ten comments (based on number of child comments) for a specific story.
 
 ```bash
URL - http://localhost:8080/news/comments?storyId=$storyId (GET)
```
**Param** - $storyId - Story id for which comments are to retrieved.

**Response** - 
```json
     { 
     "text": "Lots of HN comments are here like &quot;I know people who prefer to be contractors&quot;, &quot;My sister&#x27;s neighbours little brother actually doesn&#x27;t want to be treated with respect by Uber&quot;, blah<p>This is utter nonsense. Being a contractor means being self employed. You don&#x27;t get paid leave or sick pay. It&#x27;s a risk taking and doesn&#x27;t make any sense in the gig economy where the wages are close to minimum rates.<p>If you are truly self employed then you have a skill which you can market, you can charge fees which allows one to put money aside for things like unpaid leave or sick days.<p>If you are truly self employed then part of your job is building customer relationship, building up recurring customers, increasing your business through marketing, word of mouth, etc.<p>You actually have to do entrepreneurial shit.<p>Uber drivers are NONE of this.<p>They clock into an app which belongs to someone else and they have no share in. They can&#x27;t hand out business cards to customers or market their services on a board somewhere. They sit and wait until their master (Uber&#x2F;Lyft) assigns a ride to them. Then they have merely the chance to accept&#x2F;decline it. They can&#x27;t even negotiate their own fees. That itself is fucked up. It&#x27;s complete nonsense in extremely disingenuous by anyone here to suggest that an Uber driver is a self employed contractor and that this is good for them. They get paid shit and can&#x27;t save up like a real self employed person, because they also have no say in the ride prices.<p>Fuck the gig economy. All gig workers are slaves and we should treat each other with more dignity and upgrade them to employees. It&#x27;s our bloody duty as a decent human being.",
            "userSince": "4 years",
            "by": "dustinmoris"
        },
        {
            "text": "It strikes me that these articles are always biased in the direction of the benefits of being an employee. I have several friends that actively choose to be contractors because they prefer the (legally protected) flexibility to decide their own hours, among other things. It&#x27;s a personal decision, and there are upsides and downsides in both directions.<p>Sure - some (non-insignificant) portion of Uber and Lyft drivers would like to be employees. But surely some (also non-insignificant) portion would prefer to be contractors for Uber and Lyft and keep the legal protections that come with that.<p>These articles always make it seem like it&#x27;s a no-brainer win for all drivers no matter what, but it&#x27;s never seemed so clear cut to me.",
            "userSince": "3 years",
            "by": "rdgthree"
        }
 ```     
 
 *Cached - This API is cached for 15 minutes, so it will return the refresh data after 15 minutes.*
 

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
Aditya Soni - adityasoni182@gmail.com.
