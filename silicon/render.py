import io
import os
import tempfile
from PIL import Image
from silicon.core import Schematic

__preamble__ = r'''
\documentclass[10pt,a4paper,fleqn]{article}
\usepackage[utf8]{inputenc}
\usepackage[T1]{fontenc}
\usepackage{amssymb}
\usepackage{bbold}
\usepackage[euler]{textgreek}
\usepackage[polish]{babel}
\usepackage{polski}
\usepackage[margin=1in]{geometry}
\usepackage{amsmath}
\usepackage[font=small,labelfont=bf,figurename=Ryc.,tablename=Tabela]{caption}
\usepackage{pgfplots}
\usepackage{graphicx}
\usepackage[siunitx]{circuitikz}
\pagenumbering{gobble}
\usetikzlibrary{positioning, fit, calc}
'''


class ImageRenderer:
    def __init__(self):
        self.temp_dir = tempfile.TemporaryDirectory()
        self.temp_path = self.temp_dir.name
        self.image: Image | None = None

    def __del__(self):
        self.temp_dir.cleanup()

    def render(self, schematic: Schematic, *, verbose: bool = False) -> None:
        stream = io.StringIO()
        schematic.render(target=stream)
        doc = f'{__preamble__}\n\\begin{{document}}\n{stream.getvalue()}\\end{{document}}'
        if verbose:
            print(f'Generated LaTeX document:\n{doc}')
        with open(f'{self.temp_path}/image.tex', 'w') as f:
            f.write(doc)
        os.system(f'pdflatex -output-directory {self.temp_path} {self.temp_path}/image.tex '
                  f'-interaction=nonstopmode{'' if verbose else '> / dev / null'}')
        os.system(f'convert -density 600 {self.temp_path}/image.pdf {self.temp_path}/image.png'
                  f'{'' if verbose else '> / dev / null'}')
        im = Image.open(f'{self.temp_path}/image.png')
        self.image = im.crop(im.getbbox())

    def show(self, use_pyplot: bool = False) -> None:
        if self.image is None:
            raise ValueError('No image to show. Call render() first.')
        if use_pyplot:
            from matplotlib import pyplot as plt
            _, ax = plt.subplots(figsize=(10, 10))
            ax.axis('off')
            ax.imshow(self.image)
            plt.show()
        else:
            self.image.show()
