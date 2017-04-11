
[![Build Status](https://travis-ci.org/koddo/rueda-standard.svg?branch=master)](https://travis-ci.org/koddo/rueda-standard)

# Run

Dependencies first:

``` Shell
$ docker-compose run --rm --no-deps figwheel lein deps
```

Then run:

``` Shell
$ docker-compose up -d
```

## Cljs repl

To connect to the nrepl, do `M-x my-cider-connect`, see my .emacs.d, then try this to see effect in the browser:

``` Clojure
(js/console.log "hello world")
```

# Tests

``` Shell
$ lein clean
$ lein doo phantom test once

```

TODO: write actual tests
TODO: turn build-on-push on in travis

# Routes

If you want links like `/#/about`, replace `(hook-routes)` with `(hook-routes-with-octothorp-prefix)`.
This is useful when you want to add an app directly to a blog post page.

TODO: flask app to serve same index.html for any route: `/`, `/about`, `/etc`, `/*`

# Publish

TODO: strip the `browser-console-logger.js` from `index.html` when publish

```
$ docker-compose run --rm --no-deps figwheel bash -c "lein clean && lein cljsbuild once min"
```

Or in a separate repo clone:

```
$ cd ~/workspace.shared-with-vm/example-cljs-reagent--for-publishing/
$ ln -s ../example-cljs-reagent/lein-deps
$ git pull && ssh vagrant@vagrant.local 'cd /mnt/hgfs/shared/example-cljs-reagent--for-publishing && docker-compose run --rm --no-deps figwheel bash -c "lein clean && lein cljsbuild once min"'
$ cp -R resources/public/* ~/workspace.example-cljs-reagent.gh-pages/
$ cp -R ~/workspace.shared-with-vm/example-cljs-reagent/resources/public/css/* ~/workspace.example-cljs-reagent.gh-pages/css/
$ cd ~/workspace.example-cljs-reagent.gh-pages/ && git add --all && git commit --allow-empty-message -am '' && git push
```


# Misc

Originally the boilerplate was generated with <https://github.com/Day8/re-frame-template>:

``` Shell
$ docker-compose --project-name projectname run --rm --no-deps figwheel lein new re-frame theproject --to-dir . +cider +routes +re-frisk
```

TODO: try `+garden`
TODO: how to abort requests? <https://github.com/Day8/re-frame-http-fx/issues/3>

The name is `theproject` everywhere, so it can be easily replaced. I don't want to spend time on finding good name for a project before writing any code.

For cljs-tools just enable custom formatters: <https://github.com/binaryage/cljs-devtools/blob/master/docs/installation.md#enable-custom-formatters-in-chrome>

# Checklist

- cider-connect


# For music example

TODO: add license for salsa music
TODO: add licence for beep from soundjay

TODO: configure cors in amazon for the bucket
I now did bucket/permissions/cors -> just saved the example with `*`

I go to dance class, and I'm a fan of rueda de casino.
It's hard for me to establish correspondence between moves and their names.

Music mp3 should to be encoded with constant bit rate, otherwise `audio.currentTime` is extremely inaccurate: <http://stackoverflow.com/questions/25468063/html5-audio-currenttime-attribute-inaccurate/37768679#37768679>  
Or use ogg vorbis.

`$ awk '{print $1}' labels.txt` or `double_beats.py labels.txt`

There are some libs to work with localstorage, but I found they don't reduce complexity, so I work with it directly, that's not hard at all.



name eight-counts type(warm-up | salsa | rueda)

warm-up
casino
side-step
triangle
square
quater turn, half turn
left turn
right turn
enchufla con vuelta
guapea




