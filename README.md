
# IVR based number guessing game using kookoo and clojure

An IVR based number guessing game.  

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


