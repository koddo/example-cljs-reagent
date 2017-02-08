(ns theproject.core)

(defmacro my-add-macro-example
  [a b]
  `(+ ~a ~b))
