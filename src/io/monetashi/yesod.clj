(ns io.monetashi.yesod)

(defmacro let-returning
  "Helper for the pattern of executing native js async which
  has to return the channel at the end"
  [bindings & body]
  {:pre [(vector? bindings)
         (= (count bindings) 2)
         (symbol? (first bindings))]}
  (if (= (count bindings) 0)
    `(do ~@body)
    `(let ~bindings
       (do ~@body)
       ~(bindings 0))))
