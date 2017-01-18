
It's ok to remove this file from the cloned repos, this won't break merging updates.

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

## Cljs repl

To connect to the nrepl, do `M-x cider-connect` and evaluate the following:

``` Clojure
(use 'figwheel-sidecar.repl-api)
(cljs-repl)
```

You can now try this to see effect in the browser:

``` Clojure
(js/console.log "hello world")
```

# Misc

Originally the boilerplate was generated with <https://github.com/Day8/re-frame-template>:

``` Shell
$ docker-compose --project-name projectname run --rm --no-deps figwheel lein new re-frame theproject --to-dir . +cider +routes +re-frisk
```

TODO: try `+test`, `+garden`

The name is `theproject` everywhere, so it can be easily replaced.



# Checklist

- cider-connect


