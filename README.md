
# IVR based number guessing game using kookoo and clojure

An IVR based number guessing game.

You can play it by dialling this number +91 40 3941 1020 and dialling pin 6829 when it asks. 

It's little slow at this point. I will try to make it faster if I get some time in next few days.

It is hosted at https://protected-brushlands-40813.herokuapp.com.

## Running Locally

Make sure you have Clojure installed.  Also, install the [Heroku Toolbelt](https://toolbelt.heroku.com/).

```sh
$ git clone https://github.com/hithacker/number-guessing-on-ivr.git
$ cd number-guessing-on-ivr
$ lein repl
user=> (require 'clojure-getting-started.web)
user=>(def server (clojure-getting-started.web/-main))
```

Your app should now be running on [localhost:5000](http://localhost:5000/).


