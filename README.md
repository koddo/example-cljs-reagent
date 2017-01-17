
# How to use

``` Shell
$ github-create-repo.sh whatever-repo-name
$ git remote add boilerplate https://github.com/koddo/my-boilerplate-cljs-reagent
$ git pull boilerplate master
```

Whenever the boilerplate updates, merge the changes with the last command above.


# Run

Dependencies first:

``` Shell
$ docker-compose --project-name projectname run --rm --no-deps figwheel lein deps
$ docker-compose --project-name projectname run --rm --no-deps gulp npm install
```

Then run:

``` Shell
$ docker-compose --project-name projectname run --rm --no-deps figwheel
$ docker-compose --project-name projectname run --rm --no-deps gulp
```


# misc

Originally the boilerplate was generated with <https://github.com/Day8/re-frame-template>:

``` Shell
$ docker-compose --project-name projectname run --rm --no-deps figwheel lein new re-frame theproject --to-dir . +cider +routes +re-frisk
```

TODO: try `+test`, `+garden`

# test

test



