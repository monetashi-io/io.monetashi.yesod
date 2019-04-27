(ns io.monetashi.yesod.promises
  (:require [promesa.core :as p]
            [cljs.core.async :refer [chan put!]]
            [io.monetashi.yesod :refer-macros [let-returning]]))


(defn to-async! [promise & [{:keys [tr-then tr-catch]
                             :or {tr-then identity
                                  tr-catch identity}}]]
  (let-returning [c (chan)]
    (-> promise
        (.then #(put! c (tr-then %)))
        (.catch #(put! c (tr-catch %))))))

