(ns io.monetashi.yesod.async
  (:require  [cljs.core.async :refer [<! >! timeout take!]]
             [cljs.core.async.impl.protocols :as impl]
             [cljs.pprint :refer [pprint]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(defn chan? [v]
  (or
   (satisfies? impl/ReadPort v)
   (satisfies? impl/WritePort v)))

(defn error? [x]
  (instance? js/Error x))


(defn throw-err [e]
  (when (error? e) (throw e))
  e)

(defn <!prn [c]
  (take! c #(if (error? %) (prn "Error: " (.-stack %)) (prn %))))

(def async-debug-repos (atom {}))

(defn async-debug! [v]
  (go-loop []
    (if (fn? v)
      (pprint (v))
      (pprint v))
    (<! (timeout 1000))
    (recur)))
