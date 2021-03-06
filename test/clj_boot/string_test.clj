(ns clj-boot.string-test
  "Test the clj-boot.string namespace.  Not API."
  (:require [clojure.test :refer :all]
            [clj-boot.string :refer :all]))


(deftest merge-strings-test
  (testing "When not merging, delimiter is nil and words are added to result"
    (is (= [["The" "blind"] nil ""]
           (-> [[] nil ""]
              (merge-strings "The")
              (merge-strings "blind")))))

  (testing "Delimiter characters"
    (testing "A word beginning and ending with a delimiter char is added to result"
      (is (= [["The" "'blind'"] nil ""]
             (-> [[] nil ""]
                (merge-strings "The")
                (merge-strings "'blind'")))))

    (testing "After a start delimiter, the delimiter is noted and words are added to 'merging'"
      (is (= [["The" "'blind'" "man" "said"] \' "'I see"]
             (-> [[] nil ""]
                (merge-strings "The")
                (merge-strings "'blind'")
                (merge-strings "man")
                (merge-strings "said")
                (merge-strings "'I")
                (merge-strings "see")))))

    (testing "On end delimiter, merged words are added to result and 'merging' state is cleared."
      (is (= [["The" "'blind'" "man" "said" "'I see you.'"] nil ""]
             (-> [[] nil ""]
                (merge-strings "The")
                (merge-strings "'blind'")
                (merge-strings "man")
                (merge-strings "said")
                (merge-strings "'I")
                (merge-strings "see")
                (merge-strings "you.'")))))))


(deftest delimited-words-test
  (testing "With balanced but not nested delimiters--happy case"
    (is (= ["The" "'blind'" "man" "said" "'I see you.'"]
           (delimited-words "The 'blind' man said 'I see you.'"))))

  (testing "When missing final delimiter does not lose words and adds the final delimiter"
    (is (= ["The" "'blind'" "man" "said" "'I see you.'"]
           (delimited-words "The 'blind' man said 'I see you."))))

  (testing "Other cases' behavior is undefined"))
