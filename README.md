# ITHome 2024 鐵人賽文- Datomic: 事件溯源資料庫

## 需要的前置準備

1. [SDKMAN](https://sdkman.io/)
2. [Clojure CLI](https://clojure.org/reference/clojure_cli)
3. 編輯器整合環境

### 解釋

- SDKMAN 是用來讓你先安裝好 java。請安裝 java 11 以上的版本。
- Clojure CLI 也是必要的，因為這邊的範例 Clojure 的程式碼。而 Clojure 跟一般的軟體開發有一個很大的不同。它需要 REPL-driven programming 的設置，所以需要設置編輯器整合。

### 編輯器整合，以 neovim 為例

-   在 mac 上安裝 neovim 
` brew install neovim `

-   安裝 vim-plug
```
sh -c 'curl -fLo "${XDG_DATA_HOME:-$HOME/.local/share}"/nvim/site/autoload/plug.vim --create-dirs \
       https://raw.githubusercontent.com/junegunn/vim-plug/master/plug.vim'
```
-   修改 `~/.config/nvim/init.vim` 
-   :source % 
-   :PlugInstall



```
" Specify a directory for plugins.
call plug#begin(stdpath('data') . '/plugged')

" Specify your required plugins here.

" better default
Plug 'liuchengxu/vim-better-default'

" Syntax highlights                                                                                                               
Plug 'clojure-vim/clojure.vim'   

" Conjure
Plug 'Olical/conjure'

" s-expression editing
Plug 'guns/vim-sexp'
Plug 'tpope/vim-sexp-mappings-for-regular-people'
Plug 'tpope/vim-surround'   
Plug 'jiangmiao/auto-pairs', { 'tag': 'v2.0.0' }

" rainbow parentheses
Plug 'amdt/vim-niji'

" linter 
Plug 'w0rp/ale'

" better preview window for suggestion/ auto-complete 
Plug 'ncm2/float-preview.nvim'

" syntax highlight for fennel language
Plug 'bakpakin/fennel.vim'

" color, look and feel 
Plug 'tomasr/molokai'
" install ack                                                                                                        
Plug 'mileszs/ack.vim' 

" Install fuzzy search
Plug 'cloudhead/neovim-fuzzy'

call plug#end()

" Place configuration AFTER `call plug#end()`!

let g:float_preview#docked = 0
let g:float_preview#max_width = 80
let g:float_preview#max_height = 40

let g:ale_linters = {
      \ 'clojure': ['clj-kondo', 'joker']
      \}
let maplocalleader=","
let g:ackprg = 'ack --nogroup --nocolor --column'  
" mapping for fuzzy search
nnoremap <C-p> :FuzzyOpen<CR>

tnoremap <Esc> <C-\><C-n> 

runtime! plugin/default.vim
set nonu
set norelativenumber
colorscheme molokai

function! Cljfmt()
 !cljfmt fix %
 " :e is to force reload the file after it got formatted.
 :e
endfunction

function! CljfmtSlow()
 !clojure -Sdeps '{:deps {dev.weavejester/cljfmt {:mvn/version "0.10.6"}}}' -m cljfmt.main fix %
 " :e is to force reload the file after it got formatted.
 :e
endfunction                                                                                  
                                                                                                
function! Sqlfmt()                                                                              
 !sqlformat --reindent --keywords upper --identifiers lower % -o %                              
 :e                                                                                             
endfunction

function! Yamlfmt()
 " pip install yamlfix
 !yamlfix %
 :e
endfunction

autocmd BufWritePost *.clj call Cljfmt()
autocmd BufWritePost *.cljs call Cljfmt()
```

## 簡易 Conjure 指令

主要會使用到的 Conjure 指令：
:ConjureEval [code]                類似 fireplace 的 cqp
`<localleader> ee`   // evaluate the form under the cursor.  (e 是 inner 的諧音)
`<localleader> er`    //  evaluate the root form under the cursor. (r 是 outer 的諧音)
`<localleader> e!`     // evaluate the form under the cursor and replace it.  (! 意指 side effect)

類似 fireplace     :Require 的功能，重新載入整個檔案。
`<localleader> eb`   // evaluate the current buffer
`<localleader> ef`   // evaluate the current file from disk

快速檢查特定變數的『值』
`<localleader> ew`  // evaluate the current word

開啟 log buffer  的指令
`<localleader> ls`            // 水平開啟
`<localleader> lv`           //  垂直開啟
`<localleader> lq`           //   關閉

看文章、切換到定義
`K`              // 看 document
`gd`            //  跳轉定義

