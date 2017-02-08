
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
$ docker-compose run --rm --no-deps figwheel lein deps
$ docker-compose run --rm --no-deps gulp npm install
```

Then run:

``` Shell
$ docker-compose up -d
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


# Tests

``` Shell
$ lein clean
$ lein doo phantom test once

```

TODO: write actual tests


# Routes

If you want links like `/#/about`, uncomment this line: `(secretary/set-config! :prefix "#")` in init.
This is useful when you want to add an app directly to a blog post page.

TODO: flask app to serve index.html for any path: `/`, `/about`, `/etc`, `/*`.

# Misc

Originally the boilerplate was generated with <https://github.com/Day8/re-frame-template>:

``` Shell
$ docker-compose --project-name projectname run --rm --no-deps figwheel lein new re-frame theproject --to-dir . +cider +routes +re-frisk
```

TODO: spec for app state
TODO: cljs-devtools: <https://github.com/Day8/re-frame/blob/master/docs/FAQs/Inspecting-app-db.md>
TODO: try `+garden`

The name is `theproject` everywhere, so it can be easily replaced. I don't want to spend time on finding good name for a project before writing any code.

For cljs-tools, enable custom formatters <https://github.com/binaryage/cljs-devtools/blob/master/docs/installation.md#enable-custom-formatters-in-chrome>


# Checklist

- cider-connect


